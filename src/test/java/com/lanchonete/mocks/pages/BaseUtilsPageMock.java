package com.lanchonete.mocks.pages;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class BaseUtilsPageMock<T> implements Page<T> {

    @Override
    public int getNumber() {

        return 0;
    }

    @Override
    public int getSize() {

        return 0;
    }

    @Override
    public int getNumberOfElements() {

        return 0;
    }

    @Override
    public List<T> getContent() {

        return null;
    }

    @Override
    public boolean hasContent() {

        return false;
    }

    @Override
    public Sort getSort() {

        return null;
    }

    @Override
    public boolean isFirst() {

        return false;
    }

    @Override
    public boolean isLast() {

        return false;
    }

    @Override
    public boolean hasNext() {

        return false;
    }

    @Override
    public boolean hasPrevious() {

        return false;
    }

    @Override
    public Pageable nextPageable() {

        return null;
    }

    @Override
    public Pageable previousPageable() {

        return null;
    }

    @Override
    public Iterator<T> iterator() {

        return null;
    }

    @Override
    public int getTotalPages() {

        return 0;
    }

    @Override
    public long getTotalElements() {

        return 0;
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {

        return null;
    }

    public List<T> content;
    public int totalElements;
    public int totalPages;
}