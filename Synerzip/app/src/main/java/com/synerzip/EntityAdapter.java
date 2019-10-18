package com.synerzip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class EntityAdapter extends ListAdapter<Entity, EntityAdapter.EntityHolder > {


    private OnItemClickListener listener;
    private Context mContext;

    public EntityAdapter(Context ctx) {
        super(DIFF_CALLBACK);
        mContext = ctx;
    }

    private static final DiffUtil.ItemCallback<Entity> DIFF_CALLBACK = new DiffUtil.ItemCallback<Entity>() {
        @Override
        public boolean areItemsTheSame(@NonNull Entity oldItem, @NonNull Entity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Entity oldItem, @NonNull Entity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getApiID().equals(newItem.getApiID());
        }
    };

    @NonNull
    @Override
    public EntityHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.entity_item,viewGroup,false);
        return new EntityHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntityHolder noteHolder, int i) {
            Entity currentEntity = getItem(i);
            noteHolder.txtName.setText(currentEntity.getName());
            //https://cdn.pixabay.com/photo/2017/11/06/18/39/apple-2924531_960_720.jpg
            /*Picasso.(mContext)
                    .load("https://is4-ssl.mzstatic.com/image/thumb/Purple123/v4/f3/13/da/f313dac4-a9a2-63d6-3826-d19eab674061/AppIcon-0-1x_U007emarketing-0-0-GLES2_U002c0-512MB-sRGB-0-0-0-85-220-0-0-0-7.png/53x53bb.png")
                    .error(R.drawable.ic_launcher_background)
                    .into(noteHolder.imageView);*/
            Picasso.get()
                    .load(currentEntity.getImage())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .tag(mContext)
                    .into(noteHolder.imageView);
            noteHolder.text_title.setText(currentEntity.getTitle());

    }


    public Entity getEntityAt(int position){
        return getItem(position);
    }

    class EntityHolder extends RecyclerView.ViewHolder{
        private TextView txtName, text_title;
        private RelativeLayout rlParent;
        private ImageView imageView;

        public EntityHolder(@NonNull View itemView) {
            super(itemView);

            rlParent = itemView.findViewById(R.id.rlParent);
            txtName = itemView.findViewById(R.id.text_name);
            imageView = itemView.findViewById(R.id.imageView);
            text_title = itemView.findViewById(R.id.text_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Entity note);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
