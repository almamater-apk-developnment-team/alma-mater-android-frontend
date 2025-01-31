package com.nitt.administration.viewModels

import com.nitt.administration.response.AdminPost

object PostRepository {
    private val posts = mutableMapOf<String, AdminPost>()

    fun savePost(post: AdminPost) {
        posts[post.postId] = post
    }

    fun getPost(postId: String): AdminPost? {
        return posts[postId]
    }
}

