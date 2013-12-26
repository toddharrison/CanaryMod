package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTBase;

import java.util.Iterator;

/**
 * Magic
 *
 * @param <E>
 *
 * @author Jason (darkdiplomat)
 */
class ListTagIterator<E extends BaseTag> implements Iterator<E> {
    protected Iterator<?> nativeIterator;

    ListTagIterator(Iterator<?> nativeIterator) {
        this.nativeIterator = nativeIterator;
    }

    @Override
    public boolean hasNext() {
        return nativeIterator.hasNext();
    }

    @SuppressWarnings("unchecked")
    @Override
    public E next() {
        return (E) CanaryBaseTag.wrap((NBTBase) nativeIterator.next());
    }

    @Override
    public void remove() {
        nativeIterator.remove();
    }
}
