package org.example.service.service;

import org.example.model.Note;
import org.example.repository.NoteRepository;
import org.example.service.NoteServiceImplement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@SpringBootTest
public class NoteServiceIntegrationTest {

    @MockBean
    private NoteRepository noteRepository;

    @Autowired
    private NoteServiceImplement noteService;

    @Test
    @DisplayName("getAllNotesTest")
    public void getAllNotesTest() {
        // Предпосылка

        NoteRepository noteRepository = mock(NoteRepository.class);

        NoteServiceImplement noteService = new NoteServiceImplement(noteRepository);


        // Создаем список и указываем Mockito, что при вызове метода findAll()
        // у мока noteRepository нужно возвращать этот список.
        List<Note> expectedNotes = Arrays.asList(new Note(), new Note());
        Mockito.when(noteRepository.findAll()).thenReturn(expectedNotes);

        // Вызов

        // Вызываем тестируемый метод getAllNotes() из noteService.
        List<Note> actualNotes = noteService.getAllNotes();

        // Проверка

        // Проверяем, что полученный список actualNotes совпадает с ожидаемым списком expectedNotes.
        assertEquals(expectedNotes, actualNotes);

    }

    @Test
    @DisplayName("getNoteByIdIfExistTest")
    public void getNoteByIdIfExistTest() {
        // Предпосылка

        // Создаем экземпляр Note
        Long id = 1L;
        Note expectedNote = new Note();
        // Указываем Mockito, что при вызове метода findById() у мока noteRepository нужно возвращать этот экземпляр.
        Mockito.when(noteRepository.findById(id)).thenReturn(Optional.of(expectedNote));

        // Вызов

        // Вызываем тестируемый метод getNoteById() из noteService.
        Note actualNote = noteService.getNoteById(id);

        // Проверка

        // Проверяем, что полученный объект actualNote совпадает с ожидаемым объектом expectedNote.
        assertEquals(expectedNote, actualNote);

        // Проверяем, что вызывается метод findById() у мока noteRepository.
        verify(noteRepository, times(1)).findById(id);

    }

    @Test
    @DisplayName("getNoteByIdNotExist")
    public void getNoteByIdNotExist() {
        // Предпосылка

        // Создаем пустой экземпляр
        Long id = 1L;
        Mockito.when(noteRepository.findById(id)).thenReturn(Optional.empty());

        // Вызов

        // Вызываем тестируемый метод getNoteById() из noteService.
        Note actualNote = noteService.getNoteById(id);

        // Проверка

        // Проверяем, что полученный объект actualNote равен null.
        assertNull(actualNote);
        // verify проверяет вызов метода findById у мока noteRepository
        verify(noteRepository, times(1)).findById(id);


    }

    @Test
    @DisplayName("createNoteTest")
    public void createNoteTest() {
        // Предпосылка

        // Создаем экземпляр Note
        Note note = new Note();
        Mockito.when(noteRepository.save(note)).thenReturn(note);

        // Вызов

        // Вызываем тестируемый метод createNote() из noteService.
        Note createdNote = noteService.createNote(note);

        // Проверка

        // Проверяем, что полученный объект createdNote совпадает с ожидаемым объектом note.
        assertEquals(note, createdNote);
        // verify проверяет вызов метода save у мока noteRepository
        verify(noteRepository, times(1)).save(note);
    }

    @Test
    @DisplayName("updateNoteTest")
    public void updateNoteTest() {
        // Предпосылка


        // Создаем экземпляр Note
        Long id = 1L;
        Note existingNote = new Note();
        // Создаем новый экземпляр Note для обновления
        Note updatedNote = new Note();
        updatedNote.setTitle("Updated Title");
        updatedNote.setContent("Updated Content");
        // Указываем Mockito, что при вызове метода findById() у мока noteRepository нужно возвращать этот экземпляр.
        Mockito.when(noteRepository.findById(id)).thenReturn(Optional.of(existingNote));
        Mockito.when(noteRepository.save(existingNote)).thenReturn(updatedNote);

        // Вызов

        // Вызываем тестируемый метод updateNote() из noteService.
        Note resultNote = noteService.updateNote(id, updatedNote);

        // Проверка

        // Проверяем, что полученный объект resultNote совпадает с ожидаемым объектом updatedNote.
        assertEquals(updatedNote, resultNote);
        // Проверяем, что полученный объект resultNote совпадает с ожидаемым объектом existingNote.
        assertEquals(updatedNote.getTitle(), existingNote.getTitle());
        // Проверяем, что полученный объект resultNote совпадает с ожидаемым объектом existingNote.
        assertEquals(updatedNote.getContent(), existingNote.getContent());
        // verify
        verify(noteRepository, times(1)).findById(id);
        verify(noteRepository, times(1)).save(existingNote);
    }

    @Test
    @DisplayName("deleteNoteByIdTest")
    public void deleteNoteByIdTest() {
        // Предпосылка

        // Создаем экземпляр Note
        Long id = 1L;
        Note note = new Note();
        Mockito.when(noteRepository.findById(id)).thenReturn(Optional.of(note));

        // Вызов

        // Вызываем тестируемый метод deleteNoteById() из noteService.
        noteService.deleteNoteById(id);

        // Проверка
        // Проверяем, что вызывается метод delete() у мока noteRepository.
        verify(noteRepository, times(1)).delete(note);
    }
}
