package com.nameisjayant.notessss.features.notes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nameisjayant.notessss.data.local.model.Note
import com.nameisjayant.notessss.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.kotlin.notifications.InitialResults
import io.realm.kotlin.notifications.UpdatedResults
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _notesAddedEventFlow: MutableSharedFlow<RealmStates<Note>> = MutableSharedFlow()
    var notesAddedEventFlow = _notesAddedEventFlow.asSharedFlow()
        private set

    private val _updateNoteEventFlow: MutableSharedFlow<RealmStates<Note>> = MutableSharedFlow()
    var updateNoteEventFlow = _updateNoteEventFlow.asSharedFlow()
        private set

    private val _deleteNoteEventFlow: MutableSharedFlow<RealmStates<String>> = MutableSharedFlow()
    var deleteNoteEventFlow = _deleteNoteEventFlow.asSharedFlow()
        private set

    private val _notesEventFlow: MutableStateFlow<CommonResponseList<Note>> = MutableStateFlow(
        CommonResponseList()
    )
    var notesEventFlow = _notesEventFlow.asStateFlow()
        private set

    private val _editNote: MutableStateFlow<Note?> = MutableStateFlow(null)
    var editNote = _editNote.asStateFlow()
        private set

    fun setNote(notes: Note?) {
        _editNote.value = notes
    }

    fun onEvent(event: NotesEvent) = viewModelScope.launch {
        when (event) {
            is NotesEvent.AddNotesEvent -> {
                _notesAddedEventFlow.emit(RealmStates.Loading)
                try {
                    val note = repository.insertNotes(event.note)
                    _notesAddedEventFlow.emit(RealmStates.Success(note))
                } catch (e: Exception) {
                    _notesAddedEventFlow.emit(RealmStates.Failure(e.message.toString()))
                }
            }

            NotesEvent.ShowNotesEvent -> {
                repository.getAllNotes()
                    .onStart {
                        _notesEventFlow.value = CommonResponseList(
                            isLoading = true
                        )
                    }
                    .catch { e ->
                        _notesEventFlow.value = CommonResponseList(
                            error = e.message.toString()
                        )
                    }
                    .collect { result ->
                        when (result) {
                            is InitialResults -> {
                                _notesEventFlow.value = CommonResponseList(
                                    data = result.list.toList()
                                )
                            }
                            is UpdatedResults -> {}
                        }
                    }
            }

            is NotesEvent.DeleteNoteEvent -> {
                _deleteNoteEventFlow.emit(RealmStates.Loading)
                try {
                    repository.deleteNote(event.id)
                    _deleteNoteEventFlow.emit(RealmStates.Success("Note Deleted"))
                } catch (e: Exception) {
                    _deleteNoteEventFlow.emit(RealmStates.Failure(e.message.toString()))
                }
            }

            is NotesEvent.UpdateNoteEvent -> {
                _updateNoteEventFlow.emit(RealmStates.Loading)
                try {
                    val note = repository.updateNote(event.note)
                    _updateNoteEventFlow.emit(RealmStates.Success(note ?: Note()))
                } catch (e: Exception) {
                    _updateNoteEventFlow.emit(RealmStates.Failure(e.message.toString()))
                }
            }
        }
    }
}


// states
sealed class RealmStates<out T> {

    data class Success<T>(val data: T) : RealmStates<T>()
    data class Failure(val error: String) : RealmStates<Nothing>()
    data object Loading : RealmStates<Nothing>()
}

data class CommonResponseList<T>(
    val data: List<T> = emptyList(), val error: String = "", val isLoading: Boolean = false
)


// events
sealed class NotesEvent {

    data class AddNotesEvent(val note: Note) : NotesEvent()
    data object ShowNotesEvent : NotesEvent()
    data class DeleteNoteEvent(val id: String) : NotesEvent()
    data class UpdateNoteEvent(val note: Note) : NotesEvent()

}