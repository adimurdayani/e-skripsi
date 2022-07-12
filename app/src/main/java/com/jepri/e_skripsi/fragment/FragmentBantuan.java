package com.jepri.e_skripsi.fragment;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.jepri.e_skripsi.R;

public class FragmentBantuan extends Fragment {
    View view;
    RelativeLayout relativeLayout1, relativeLayout2;
    ImageView btnekspan1, btnekspan2, btn_kembali;
    LinearLayout ekspan1, ekspan2;

    public FragmentBantuan() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bantuan, container, false);
        init();
        return view;
    }

    private void init() {
        relativeLayout1 = view.findViewById(R.id.relative_layout);
        ekspan1 = view.findViewById(R.id.ekspan1);
        btnekspan1 = view.findViewById(R.id.btnekspan1);
        relativeLayout2 = view.findViewById(R.id.relative_layout2);
        ekspan2 = view.findViewById(R.id.ekspan2);
        btnekspan2 = view.findViewById(R.id.btnekspan2);
        btn_kembali = view.findViewById(R.id.btn_kembali);

        btn_kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frm_menu, new FragmentOption())
                        .commit();
            }
        });

        btnekspan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ekspan1.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(relativeLayout1, new AutoTransition());
                    ekspan1.setVisibility(View.GONE);
                    btnekspan1.setImageResource(R.drawable.ic_more);
                } else {
                    TransitionManager.beginDelayedTransition(relativeLayout1,
                            new AutoTransition());
                    ekspan1.setVisibility(View.VISIBLE);
                    btnekspan1.setImageResource(R.drawable.ic_expand_less);
                }
            }
        });
        btnekspan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ekspan2.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(relativeLayout2, new AutoTransition());
                    ekspan2.setVisibility(View.GONE);
                    btnekspan2.setImageResource(R.drawable.ic_more);
                } else {
                    TransitionManager.beginDelayedTransition(relativeLayout2,
                            new AutoTransition());
                    ekspan2.setVisibility(View.VISIBLE);
                    btnekspan2.setImageResource(R.drawable.ic_expand_less);
                }
            }
        });
    }
}
