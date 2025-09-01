package com.mahesh.messageservice.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.mahesh.messageservice.model.ChatMessage;
import com.mahesh.messageservice.repository.MessageRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class ChatController {
	private final MessageRepository messageRepository;

	public ChatController(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}

	@MessageMapping("/sendMessage")
	@SendTo("/topic/messages")
	public ChatMessage sendMessage(ChatMessage chatMessage) {
		log.info("chat message : {}",chatMessage);
		return messageRepository.save(chatMessage);
	}
}
