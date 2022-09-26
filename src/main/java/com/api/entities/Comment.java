package com.api.entities;

import lombok.Data;

@Data
public class Comment {
  String postId;
  int id;
  String name;
  String email;
  String body;
}
