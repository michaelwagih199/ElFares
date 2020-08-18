package com.polimigo.elfares.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.polimigo.elfares.R;
import com.polimigo.elfares.activities.PlayerActivity;
import com.polimigo.elfares.activities.VedioView;
import com.polimigo.elfares.activities.vedioPlayer;
import com.polimigo.elfares.entities.AppSettingModel;
import com.polimigo.elfares.entities.MatchModel;
import java.util.ArrayList;

import kotlincodes.com.retrofitwithkotlin.retrofit.ApiClient;
import retrofit2.Call;


public class MultipleTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<MatchModel> matchList;
    private final int TYPE_MASTER = 1;
    private final int TYPE_DETAILS = 2;
    private InterstitialAd mInterstitialAd;
    boolean review;

    public MultipleTypeAdapter(Context context, ArrayList<MatchModel> employees, boolean review) {

        this.context = context;
        this.matchList = employees;
        this.review = review;

        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544~3347511713");
        // Create an ad request.
        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
        mInterstitialAd.loadAd(adRequestBuilder.build());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == TYPE_MASTER) { // for call layout
            view = LayoutInflater.from(context).inflate(R.layout.maches_main_row, viewGroup, false);
            return new MasterViewHolder(view);

        } else { // for email layout
            view = LayoutInflater.from(context).inflate(R.layout.matches_secodery_item, viewGroup, false);
            return new DetailsViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (matchList.get(position).isSelected()) {
            Log.d("item click", " TYPE_MASTER ");
            return TYPE_DETAILS;
        } else {
            Log.d("item click", " TYPE_DETAILS ");
            return TYPE_MASTER;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_MASTER) {
            ((MasterViewHolder) viewHolder).setCallMaster(matchList.get(position));
        } else if (getItemViewType(position) == TYPE_DETAILS) {
            ((DetailsViewHolder) viewHolder).setEmailDetails(matchList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    class MasterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLeague, tvNameT1, tvNameT2, tvDay, tvTime;
        private ImageView imgVT1Logo, imgVT2Logo;

        MasterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLeague = itemView.findViewById(R.id.tvLeague);
            tvNameT1 = itemView.findViewById(R.id.tvNameT1);
            tvNameT2 = itemView.findViewById(R.id.tvNameT2);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvTime = itemView.findViewById(R.id.tvTime);
            imgVT1Logo = itemView.findViewById(R.id.imgVT1Logo);
            imgVT2Logo = itemView.findViewById(R.id.imgVT2Logo);
        }

        void setCallMaster(final MatchModel employee) {

            tvLeague.setText(employee.getLeague());
            tvNameT1.setText(employee.getTeams().get(0).getName());
            tvNameT2.setText(employee.getTeams().get(1).getName());
            tvDay.setText(employee.getDay());
            tvTime.setText(employee.getTime());
            Glide
                    .with(context) // give it the context
                    .load(employee.getTeams().get(0).getLogo()) // load the image
                    .error(R.drawable.ic_baseline_error_24)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(imgVT1Logo);// select the ImageView to load it into
            // load the image with Picasso
            Glide
                    .with(context) // give it the context
                    .load(employee.getTeams().get(1).getLogo()) // load the image
                    .error(R.drawable.ic_baseline_error_24)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(imgVT2Logo); // select the ImageView to load it into
            itemView.setOnClickListener(view -> {
                employee.setSelected(true);
                notifyDataSetChanged();
            });

        }
    }


    class DetailsViewHolder extends RecyclerView.ViewHolder {

        private TextView txtVmatchName, tvNameT2Details, tvNameT1Details, tvTimeDetail, tvChannelName;
        private Button btnViewMatchDetail;
        private ImageView imgVT2LogoDetails, imgVT1LogoDetails;

        DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            txtVmatchName = itemView.findViewById(R.id.txtVmatchName);
            tvNameT2Details = itemView.findViewById(R.id.tvNameT2Details);
            tvNameT1Details = itemView.findViewById(R.id.tvNameT1Details);
            tvTimeDetail = itemView.findViewById(R.id.tvTimeDetail);
            tvChannelName = itemView.findViewById(R.id.tvChannelName);
            btnViewMatchDetail = itemView.findViewById(R.id.btnViewMatchDetail);
            imgVT2LogoDetails = itemView.findViewById(R.id.imgVT2LogoDetails);
            imgVT1LogoDetails = itemView.findViewById(R.id.imgVT1LogoDetails);

        }

        void setEmailDetails(final MatchModel employee) {

            txtVmatchName.setText(employee.getLeague());
            tvNameT2Details.setText(employee.getTeams().get(0).getName());
            tvNameT1Details.setText(employee.getTeams().get(1).getName());
            tvTimeDetail.setText(employee.getTime());
            tvChannelName.setText(employee.getChannel());
            Glide
                    .with(context) // give it the context
                    .load(employee.getTeams().get(0).getLogo()) // load the image
                    .error(R.drawable.ic_baseline_error_24)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(imgVT1LogoDetails);// select the ImageView to load it into
            // load the image with Picasso
            Glide
                    .with(context) // give it the context
                    .load(employee.getTeams().get(1).getLogo()) // load the image
                    .error(R.drawable.ic_baseline_error_24)
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(imgVT2LogoDetails); // select the ImageView to load it into

            btnViewMatchDetail.setOnClickListener(view -> {
                Intent intent = new Intent(context, VedioView.class);
                intent.putExtra("EXTRA_SESSION_ID", employee.getLink());

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                // Set an AdListener.
                mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        Toast.makeText(context,
                                "The interstitial is loaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdClosed() {
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    }
                });
            });

            if (review == true){
                btnViewMatchDetail.setVisibility(View.INVISIBLE);
            }

            itemView.setOnClickListener(view ->
            {
                employee.setSelected(false);
                notifyDataSetChanged();
            });

        }
    }

}
