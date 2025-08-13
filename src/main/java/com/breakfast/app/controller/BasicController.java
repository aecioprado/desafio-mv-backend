package com.breakfast.app.controller;

import org.springframework.http.ResponseEntity;

public interface BasicController {

    public ResponseEntity<?> create();
    public ResponseEntity<?> update();
    public ResponseEntity<?> getById();
    public ResponseEntity<?> getAll();
    public ResponseEntity<?> delete();
}
