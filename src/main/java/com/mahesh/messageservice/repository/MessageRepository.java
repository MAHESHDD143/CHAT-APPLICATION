package com.mahesh.messageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mahesh.messageservice.model.ChatMessage;

public interface MessageRepository extends JpaRepository<ChatMessage, Long> {

}
