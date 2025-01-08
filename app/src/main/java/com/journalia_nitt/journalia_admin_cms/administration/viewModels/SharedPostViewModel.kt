package com.journalia_nitt.journalia_admin_cms.administration.viewModels

import androidx.lifecycle.ViewModel
import com.journalia_nitt.journalia_admin_cms.administration.response.AdminPost

object PostRepository {
    private val posts = mutableMapOf<String, AdminPost>()

    fun savePost(post: AdminPost) {
        posts[post.postId] = post
    }

    fun getPost(postId: String): AdminPost? {
        return posts[postId]
    }
}

