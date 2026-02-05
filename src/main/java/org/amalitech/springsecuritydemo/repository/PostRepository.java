package org.amalitech.springsecuritydemo.repository;

import org.amalitech.springsecuritydemo.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}