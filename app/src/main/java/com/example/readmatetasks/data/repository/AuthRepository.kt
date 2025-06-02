package com.example.readmatetasks.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await

/**
 * Repositorio de autenticación que maneja el registro, inicio de sesión
 * y recuperación de datos de usuario usando Firebase Authentication y Firestore.
 * La imagen de perfil se guarda en almacenamiento interno del dispositivo.
 *
 * @property firebaseAuth Instancia de FirebaseAuth para gestionar la autenticación.
 * @property firestore Instancia de FirebaseFirestore para almacenar información del usuario.
 */
class AuthRepository(
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun registerUser(email: String, password: String, username: String): Result<Unit> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return Result.failure(Exception())
            val saveResult = saveUserDetails(userId, username, email)
            if (saveResult.isSuccess) {
                Result.success(Unit)
            } else {
                Result.failure(saveResult.exceptionOrNull() ?: Exception())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<Unit> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private suspend fun saveUserDetails(
        userId: String,
        username: String,
        email: String
    ): Result<Unit> {
        return try {
            val userData = mapOf(
                "id" to userId,
                "username" to username,
                "email" to email,
                "createdAt" to Timestamp.now()
            )
            firestore.collection("Users").document(userId).set(userData).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserDetails(userId: String): Result<Map<String, Any>> {
        return try {
            val snapshot = firestore.collection("Users")
                .document(userId)
                .get()
                .await()
            if (snapshot.exists()) {
                Result.success(snapshot.data ?: emptyMap())
            } else {
                Result.failure(Exception())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    fun logoutUser() {
        firebaseAuth.signOut()
    }

    /**
     * Guarda la imagen de perfil en la memoria interna del dispositivo.
     */
    fun saveProfileImage(context: Context, userId: String, bitmap: Bitmap): Boolean {
        return try {
            val file = File(context.filesDir, "$userId-profile.jpg")
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Obtiene la imagen de perfil desde la memoria interna del dispositivo.
     */
    fun getProfileImage(context: Context, userId: String): Bitmap? {
        return try {
            val file = File(context.filesDir, "$userId-profile.jpg")
            if (file.exists()) {
                BitmapFactory.decodeFile(file.absolutePath)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Elimina la imagen de perfil almacenada localmente.
     */
    fun deleteProfileImage(context: Context, userId: String): Boolean {
        return try {
            val file = File(context.filesDir, "$userId-profile.jpg")
            file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * Actualiza el nombre de usuario en Firestore.
     */
    suspend fun updateUsername(userId: String, newUsername: String): Result<Unit> {
        return try {
            firestore.collection("Users").document(userId).update("username", newUsername).await()
            Result.success(Unit)
        } catch (e: Exception) { Result.failure(e) }
    }

    /**
     * Cambia la contraseña del usuario autenticado en Firebase Authentication.
     */
    suspend fun updatePassword(newPassword: String): Result<Unit> {
        return try {
            val user = firebaseAuth.currentUser
            if (user != null) {
                user.updatePassword(newPassword).await()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Usuario no autenticado"))
            }
        } catch (e: Exception) { Result.failure(e) }
    }
}
