package com.example.ga_23s1_comp2100_6442.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ga_23s1_comp2100_6442.R;
import com.example.ga_23s1_comp2100_6442.model.UserMessage;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MessageAdapter extends FirebaseRecyclerAdapter<UserMessage, RecyclerView.ViewHolder> {
    private static int TEXT_MESSAGE_TYPE = 1;
    private static int IMAGE_MESSAGE_TYPE = 2;

    FirebaseRecyclerOptions<UserMessage> data;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MessageAdapter(@NonNull FirebaseRecyclerOptions<UserMessage> options) {
        super(options);
        this.data = options;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TEXT_MESSAGE_TYPE) {
            View view =  inflater.inflate(R.layout.text_message, parent, false);
            return new TextViewHolder(view);
        } else {
            return null;
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull UserMessage model) {
        if (data.getSnapshots().get(position).getText() != null) {
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            textViewHolder.bind(model);
        }
    }



    @Override
    public int getItemViewType(int position) {
        if (data.getSnapshots().get(position).getText() == null) return IMAGE_MESSAGE_TYPE;
        return TEXT_MESSAGE_TYPE;
    }
    class TextViewHolder extends RecyclerView.ViewHolder {
        View layoutView;
        public TextViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layoutView = itemView;
        }
        public void bind(UserMessage message) {
            TextView messageContent = layoutView.findViewById(R.id.messageTextView);
            messageContent.setText(message.getText());
            TextView messengerName = layoutView.findViewById(R.id.messengerTextView);
            messengerName.setText(message.getName());
        }
    }
}
