package com.nameisjayant.notessss.features.notes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nameisjayant.notessss.data.local.model.Notes
import com.nameisjayant.notessss.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NotesRepository
) : ViewModel() {

    private val _notesAddedEventFlow: MutableSharedFlow<RealmStates<Notes>> = MutableSharedFlow()
    var notesAddedEventFlow = _notesAddedEventFlow.asSharedFlow()
        private set

    private val _notesEventFlow: MutableStateFlow<CommonResponseList<Notes>> = MutableStateFlow(
        CommonResponseList()
    )
    var notesEventFlow = _notesEventFlow.asStateFlow()
        private set

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

            NotesEvent.ShowNotes -> {
                _notesEventFlow.value = CommonResponseList(
                    isLoading = true
                )
                try {
                    _notesEventFlow.value = CommonResponseList(
                        data = repository.getAllNotes()
                    )

                } catch (e: Exception) {
                    _notesEventFlow.value = CommonResponseList(
                        error = e.message.toString()
                    )
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
    val data: List<T> = emptyList(),
    val error: String = "",
    val isLoading: Boolean = false
)


// events
sealed class NotesEvent {

    data class AddNotesEvent(val note: Notes) : NotesEvent()
    data object ShowNotes : NotesEvent()

}