package com.example.dash_coin.data.repositoryImpl

import com.example.dash_coin.core.util.Constants
import com.example.dash_coin.core.util.Resource
import com.example.dash_coin.domain.Model.CoinById
import com.example.dash_coin.domain.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseRepositoryImpl constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
): FirebaseRepository {
    override fun getUserId(): Flow<String> {
        return flow{
            firebaseAuth.currentUser?.uid?.let{
                emit(it)
            }
        }
    }

    override fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> {
        return flow{
            emit(Resource.Loading())
            emit(
                Resource.Success(
                    firebaseAuth.createUserWithEmailAndPassword(email, password).await()
                )
            )
        }.catch {
            emit(Resource.Error(it.toString()))
        }
    }

    override fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Resource<AuthResult>> {
       return flow {
           emit(Resource.Loading())
           emit(Resource.Success(firebaseAuth.signInWithEmailAndPassword(email, password).await()))
       }.catch {
           emit(Resource.Error(it.toString()))
       }
    }

    override fun isCurrentUserExist(): Flow<Boolean> {
        return flow{
            emit(firebaseAuth.currentUser != null)
        }
    }

    override fun getCurrentUserEmail(): Flow<String> {
        return flow{
            firebaseAuth.currentUser?.email?.let{
                emit(it)
            }
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun addCoinFavorite(coinById: CoinById): Flow<Resource<Task<Void>>> {
        return flow{
            emit(Resource.Loading())
            getUserId().collect{
                val favouriteRef = fireStore.collection(Constants.FAVOURITES_COLLECTION)
                    .document(it)
                    .collection("coins").document(coinById.name.orEmpty())
                    .set(coinById)

                favouriteRef.await()

                emit(Resource.Success(favouriteRef))
            }
        }.catch{
            emit(Resource.Error(it.toString()))
        }
    }

    override fun deleteCoinFavorite(coinById: CoinById): Flow<Resource<Task<Void>>> {
        return flow {
            emit(Resource.Loading())
            getUserId().collect {
                val favoriteRef = fireStore.collection(Constants.FAVOURITES_COLLECTION)
                    .document(it)
                    .collection("coins").document(coinById.name.orEmpty())
                    .delete()

                favoriteRef.await()
                emit(Resource.Success(favoriteRef))
            }
        }.catch {
            emit(Resource.Error(it.toString()))
        }
    }

    override fun isFavoriteState(coinById: CoinById): Flow<CoinById?> {
        return callbackFlow {
            getUserId().collect { userId ->
                fireStore.collection(Constants.FAVOURITES_COLLECTION)
                    .document(userId)
                    .collection("coins").document(coinById.name.orEmpty())
                    .addSnapshotListener { value, error ->
                        error?.let {
                            this.close(it)
                        }
                        value?.let {
                            val data = it.toObject(CoinById::class.java)
                            this.trySend(data)
                        }
                    }
            }
            awaitClose { this.cancel() }
        }
    }

    override fun getCoinFavorite(): Flow<Resource<List<CoinById>>> {
        return callbackFlow {
            this.trySend(Resource.Loading())
            getUserId().collect { userId ->
                val snapshot = fireStore.collection(Constants.FAVOURITES_COLLECTION)
                    .document(userId)
                    .collection("coins")
                snapshot.addSnapshotListener { value, error ->
                    error?.let {
                        this.close(it)
                    }

                    value?.let {
                        val data = value.toObjects(CoinById::class.java)
                        this.trySend(Resource.Success(data))
                    }
                }
            }
            awaitClose { this.cancel() }
        }
    }
    }
