package io.adventurous.android.api;

import io.adventurous.android.api.types.ApiType;

import java.util.List;


public interface DomainObjectQueryListener<T extends ApiType> {
    /**
     * Invoked whenever a DomainObject<T> is found by the query operation.
     *
     * @param object The current {@code <T>} found by the query.
     * @param objects The {@code List<T>} found so far, including <code>object</code>.
     */
    void onResultFound(T object, List<T> objects);
}