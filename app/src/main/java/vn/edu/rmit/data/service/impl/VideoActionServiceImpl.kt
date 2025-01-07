package vn.edu.rmit.data.service.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import vn.edu.rmit.data.service.AccountService
import vn.edu.rmit.data.service.VideoActionService
import vn.edu.rmit.data.service.VideoService
import javax.inject.Inject

class VideoActionServiceImpl @Inject constructor(
    val db: FirebaseDatabase,
    val accountService: AccountService,
) : VideoActionService {
    private val videoReactionRef = db.reference.child("video_reaction")

    override fun observeVideoReactions(videoId: String): Flow<Int> = callbackFlow {
        val reactionRef = videoReactionRef.child(videoId).child("liked")

        val listener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                trySend(dataSnapshot.childrenCount.toInt())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                close(databaseError.toException())
            }
        }
        reactionRef.addValueEventListener(listener)
        awaitClose { reactionRef.removeEventListener(listener) }
    }

    override fun observeUserReactedVideo(videoId: String, userId: String): Flow<Boolean> =
        callbackFlow {
            val userReactionRef = videoReactionRef.child(videoId).child("liked").child(userId)
            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    trySend(dataSnapshot.exists())
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    close(databaseError.toException())
                }
            }
            userReactionRef.addValueEventListener(listener)
            awaitClose { userReactionRef.removeEventListener(listener) }
        }

    override suspend fun toggleReaction(videoId: String, userId: String) {
        val userReactionRef = videoReactionRef.child(videoId).child("liked").child(userId)
        userReactionRef.get().addOnSuccessListener {
            if (it.exists())
                userReactionRef.removeValue()
            else
                userReactionRef.setValue(true)
        }
    }

    override fun observeUserSavedVideo(videoId: String, userId: String): Flow<Boolean> =
        callbackFlow {
            val userSaveRef = videoReactionRef.child(videoId).child("saved").child(userId)
            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    trySend(dataSnapshot.exists())
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    close(databaseError.toException())
                }
            }
            userSaveRef.addValueEventListener(listener)
            awaitClose { userSaveRef.removeEventListener(listener) }
        }

    override suspend fun toggleSave(videoId: String, userId: String) {
        val userSaveRef = videoReactionRef.child(videoId).child("saved").child(userId)
        userSaveRef.get().addOnSuccessListener {
            if (it.exists())
                userSaveRef.removeValue()
            else
                userSaveRef.setValue(true)
        }
    }
}