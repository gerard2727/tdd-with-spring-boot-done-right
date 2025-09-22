package de.rieckpil;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
class CommentService {

  public List<Comment> findAll() {
    return List.of();
  }

  public UUID createComment(@Valid CommentCreationRequest content, String authorName) {

    return UUID.randomUUID();
  }
}
