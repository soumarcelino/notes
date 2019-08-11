package com.m.notas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.koushikdutta.ion.Ion;
import com.m.notas.models.Note;
import com.m.notas.R;
import com.m.notas.utils.Text;

import java.util.List;

import static com.m.notas.utils.Text.truncateText;

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
        holder.noteName.setText(truncateText(note.getName(), 30));
        holder.noteDesc.setText(truncateText(note.getDescription(), 40));
        String hash = Text.MD5(note.getName().toLowerCase());
        Ion.with(layoutInflater.getContext())
                .load("https://www.gravatar.com/monsterid/"+hash+"?s=500")
                .withBitmap()
                .intoImageView(holder.gravatar);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView noteName;
        TextView noteDesc;
        ImageView gravatar;
        OnNoteListener onNoteListener;

        ViewHolder(final View itemView, OnNoteListener onNoteListener){
            super(itemView);
            noteName = itemView.findViewById(R.id.noteName);
            noteDesc = itemView.findViewById(R.id.noteDesc);
            gravatar = itemView.findViewById(R.id.gravatar);

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
