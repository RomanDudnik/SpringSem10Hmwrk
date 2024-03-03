package org.example.service.service;

import org.example.model.Note;
import org.example.repository.NoteRepository;
import org.example.service.NoteServiceImplement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class NoteServiceIntegrationTest {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NoteServiceImplement noteService;

    @Test
    @DisplayName("getAllNotesTest")
    public void getAllNotesTest() {
        // Предпосылка

        // Создаем экземпляры Note
        Note note1 = new Note();
        note1.setTitle("Note 1");
        note1.setContent("Content 1");
        noteRepository.save(note1);

        Note note2 = new Note();
        note2.setTitle("Note 2");
        note2.setContent("Content 2");
        noteRepository.save(note2);

        // Вызов

        // Вызываем тестируемый метод getAllNotes() из noteService.
        List<Note> notes = noteService.getAllNotes();

        // Проверка

        // Проверяем, что полученный список notes содержит ожидаемые экземпляры Note.
        assertEquals(2, notes.size());
        assertTrue(notes.contains(note1));
        assertTrue(notes.contains(note2));
    }

    @Test
    @DisplayName("getNoteByIdIfExistTest")
    public void getNoteByIdIfExistTest() {
        // Предпосылка

        // Создаем экземпляр Note
        Note note = new Note();
        note.setTitle("Note");
        note.setContent("Content");
        noteRepository.save(note);

        // Вызов

        // Вызываем тестируемый метод getNoteById() из noteService.
        Note retrievedNote = noteService.getNoteById(note.getId());

        // Проверка

        // Проверяем, что полученный объект retrievedNote совпадает с ожидаемым объектом note.
        assertEquals(note, retrievedNote);
    }

    @Test
    @DisplayName("getNoteByIdNotExist")
    public void getNoteByIdNotExist() {
        // Предпосылка

        // Создаем пустой экземпляр
        Long nonExistingId = 123L;

        // Вызов

        // Вызываем тестируемый метод getNoteById() из noteService.
        Note retrievedNote = noteService.getNoteById(nonExistingId);

        // Проверка

        // Проверяем, что полученный объект retrievedNote равен null.
        assertNull(retrievedNote);
    }

}
