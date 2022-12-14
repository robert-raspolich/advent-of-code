package com.raspolich.adventofcode.controller;

import com.raspolich.adventofcode.model.PuzzleId;
import com.raspolich.adventofcode.service.PuzzleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PuzzleRestController {
    private final PuzzleService puzzleService;

    public PuzzleRestController(PuzzleService puzzleService) {
        this.puzzleService = puzzleService;
    }

    @GetMapping(value = "puzzle/{year}/{day}/{puzzleNumber}")
    public ResponseEntity<String> getAnswer(@PathVariable int year, @PathVariable int day, @PathVariable int puzzleNumber) {
        PuzzleId.PuzzleNumber puzzleNo = PuzzleId.PuzzleNumber.fromInt(puzzleNumber);
        if (puzzleNo == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(puzzleService.getPuzzleAnswer(new PuzzleId(year, day, puzzleNo)));
    }
}
