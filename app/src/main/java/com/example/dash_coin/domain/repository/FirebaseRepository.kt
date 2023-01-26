package com.example.dash_coin.domain.repository

import com.example.dash_coin.core.util.Resource
import com.example.dash_coin.domain.Model.CoinById
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    fun getUserId(): Flow<String>
    fun signUpWithEmailAndPassword(email: String, password: String): Flow<Resource<AuthResult>>

    fun signInWithEmailAndPassword(email: String, password: String): Flow<Resource<AuthResult>>

    fun isCurrentUserExist(): Flow<Boolean>

    fun getCurrentUserEmail(): Flow<String>

    fun signOut()

    fun addCoinFavorite(coinById: CoinById): Flow<Resource<Task<Void>>>

    fun deleteCoinFavorite(coinById: CoinById): Flow<Resource<Task<Void>>>

    fun isFavoriteState(coinById: CoinById): Flow<CoinById?>

    fun getCoinFavorite(): Flow<Resource<List<CoinById>>>
}