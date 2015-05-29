package com.gturedi;

import java.io.Serializable;

public interface ReadCallback<T extends Serializable> {
    void done(Exception e, T result);
}
