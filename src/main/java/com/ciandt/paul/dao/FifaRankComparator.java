package com.ciandt.paul.dao;

import com.ciandt.paul.entity.FifaRank;

import java.util.Comparator;

public class FifaRankComparator implements Comparator<FifaRank> {
    @Override
    public int compare(FifaRank r1, FifaRank r2) {
        return r1.getRank() - r2.getRank();
    }
}
