package app.pavel.handbooklivedataroom.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.pavel.handbooklivedataroom.R;
import app.pavel.handbooklivedataroom.data.RoadMarking;
import app.pavel.handbooklivedataroom.utils.HandbookLiveDataRoom;

public class RoadMarkingAdapter extends
        RecyclerView.Adapter<RoadMarkingAdapter.ViewHolder>{

    public interface OnRoadMarkingClickListener {
        void onRoadMarkingClickListener(String roadMarkingTitle);
    }

    private String imageName;

    private List<RoadMarking> data;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnRoadMarkingClickListener onRoadMarkingClickListener;

    RoadMarkingAdapter(Context context, OnRoadMarkingClickListener listener) {
        this.data = new ArrayList<>();
        this.context = context;
        this.onRoadMarkingClickListener = listener;
        this.layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<RoadMarking> newData) {
        if (data != null) {
            RoadMarkingAdapter.CategoryDiffCallback categoryDiffCallback =
                    new RoadMarkingAdapter.CategoryDiffCallback(data, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(categoryDiffCallback);

            data.clear();
            data.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        }
        else {
            // first initialization
            data = newData;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvTitle;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvTitle = itemView.findViewById(R.id.textViewTitle);
        }

        void bind(final RoadMarking roadMarking) {
            if (roadMarking != null) {

                imageName = roadMarking.getImageName();

                int resID = HandbookLiveDataRoom.getContext().getResources()
                        .getIdentifier( imageName, "drawable",
                                HandbookLiveDataRoom.getThisPackageName());

                imageView.setImageResource(resID);

                tvTitle.setText(roadMarking.getTitle());

                itemView.setOnClickListener(view -> {
                    if (onRoadMarkingClickListener != null)
                        onRoadMarkingClickListener.onRoadMarkingClickListener(roadMarking.getTitle());
                });

            }
        }
    }

    class CategoryDiffCallback extends DiffUtil.Callback {

        private final List<RoadMarking> oldCategories, newCategories;

        CategoryDiffCallback(List<RoadMarking> oldCategories, List<RoadMarking> newCategories) {
            this.oldCategories = oldCategories;
            this.newCategories = newCategories;
        }

        @Override
        public int getOldListSize() {
            return oldCategories.size();
        }

        @Override
        public int getNewListSize() {
            return newCategories.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldCategories.get(oldItemPosition).getTitle()
                    .equals(newCategories.get(newItemPosition).getTitle());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldCategories.get(oldItemPosition).equals(newCategories.get(newItemPosition));
        }
    }

}