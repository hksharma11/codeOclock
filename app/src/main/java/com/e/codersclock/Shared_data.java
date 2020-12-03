package com.e.codersclock;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Shared_data {

    Context context;








    public List<String> getContestID() {
        return contestID;
    }

    public void setContestID(List<String> contestID) {
        this.contestID = contestID;
    }

    private List<String> contestID=new List<String>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<String> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] a) {
            return null;
        }

        @Override
        public boolean add(String s) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends String> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public String get(int index) {
            return null;
        }

        @Override
        public String set(int index, String element) {
            return null;
        }

        @Override
        public void add(int index, String element) {

        }

        @Override
        public String remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<String> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<String> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<String> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
    SharedPreferences sharedPreferences;


    public Shared_data(Context context){
        this.context=context;

        sharedPreferences=context.getSharedPreferences("contestID", Context.MODE_PRIVATE);
    }
}
