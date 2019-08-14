package com.m.notas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.m.notas.models.Note;
import com.m.notas.R;
import java.util.List;

import static com.m.notas.utils.DateUtils.prettyTime;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> notes;
    private LayoutInflater layoutInflater;
    private OnNoteListener mOnNoteListener;

    public NoteAdapter(Context context, List<Note> notes, OnNoteListener onNoteListener){
        this.notes = notes;
        this.layoutInflater = LayoutInflater.from(context);
        this.mOnNoteListener = onNoteListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.note_row, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.noteName.setText(note.getName());
        holder.noteDesc.setText(note.getDescription());
        holder.noteListData.setText(prettyTime(note.getCreateDate()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView noteName;
        TextView noteDesc;
        TextView noteListData;
        OnNoteListener onNoteListener;

        ViewHolder(final View itemView, OnNoteListener onNoteListener){
            super(itemView);
            noteName = itemView.findViewById(R.id.noteName);
            noteDesc = itemView.findViewById(R.id.noteDesc);
            noteListData = itemView.findViewById(R.id.noteListDate);

            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
