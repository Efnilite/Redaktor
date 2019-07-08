package com.efnilite.redaktor.event.editor;

import com.efnilite.redaktor.Editor;
import com.efnilite.redaktor.wrapper.EventWrapper;

public class EditorUndoEvent extends EventWrapper {

    private Editor<?> editor;
    private int amountOfUndos;

}
