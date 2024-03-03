package org.example.service;

import lombok.AllArgsConstructor;
import org.example.model.Note;
import org.example.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Класс для реализации сервиса Note
 * Логика работы с бд
 */

@Service
@AllArgsConstructor
public class NoteServiceImplement implements NoteService {

    /**
     * Репозиторий заметок
     */
    private final NoteRepository noteRepository;


    /**
     * Метод для получения всех заметок
     * @return
     */
    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    /**
     * Метод для получения заметки
     * @param id
     * @return
     */
    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    /**
     * Метод для создания заметки
     * @param note
     * @return
     */
    @Override
    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    /**
     * Метод для обновления заметки
     * @param note
     * @return
     */
    @Override
    public Note updateNote(Long id, Note note) {
        //получение заметки по id
        Note noteById = getNoteById(id);
        //обновление названия заметки
        noteById.setTitle(note.getTitle());
        //обновление текста заметки
        noteById.setContent(note.getContent());
        //сохранение обновленной заметки
        return noteRepository.save(noteById);
    }

    /**
     * Метод для удаления заметки
     * @param id
     */
    @Override
    public void deleteNoteById(Long id) {
        Note noteById = getNoteById(id);
        noteRepository.delete(noteById);
    }
}
