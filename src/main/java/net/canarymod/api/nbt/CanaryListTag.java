package net.canarymod.api.nbt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;

import java.lang.reflect.Array;
import java.util.*;

/**
 * ListTag wrapper implementation
 *
 * @author Greg (gregthegeek)
 * @author Jason (darkdiplomat)
 */
public class CanaryListTag<E extends BaseTag> extends CanaryBaseTag implements ListTag<E> {

    /**
     * Constructs a new wrapper for NBTTagList
     *
     * @param tag
     *         the NBTTagList to wrap
     */
    public CanaryListTag(NBTTagList tag) {
        super(tag);
    }

    /**
     * Constructs a new CanaryListTag and associated NBTTagList
     */
    public CanaryListTag() {
        super(new NBTTagList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E element) {
        return getHandle().c.add(((CanaryBaseTag)element).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {
        getHandle().c.add(index, ((CanaryBaseTag)element).getHandle());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return getHandle().c.addAll(makeRaw(c));
    }

    private Collection<NBTBase> makeRaw(Collection<?> c) {
        Collection<NBTBase> raw = new ArrayList<NBTBase>(c.size());

        for (Object o : c) {
            raw.add(((CanaryBaseTag)o).getHandle());
        }
        return raw;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return getHandle().c.addAll(index, makeRaw(c));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        getHandle().c.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object o) {
        if (o instanceof CanaryBaseTag) { // Pass handle, not actual object
            getHandle().c.contains(((CanaryBaseTag)o).getHandle());
        }
        return getHandle().c.contains(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return getHandle().c.containsAll(makeRaw(c));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E get(int index) {
        return (E)CanaryBaseTag.wrap((NBTBase)getHandle().c.get(index));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int indexOf(Object o) {
        if (o instanceof CanaryBaseTag) { // Pass handle, not actual object
            return getHandle().c.indexOf(((CanaryBaseTag)o).getHandle());
        }
        return getHandle().c.indexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return getHandle().c.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return new ListTagIterator<E>(getHandle().c.iterator());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int lastIndexOf(Object o) {
        if (o instanceof CanaryBaseTag) { // Pass handle, not actual object
            return getHandle().c.lastIndexOf(((CanaryBaseTag)o).getHandle());
        }
        return getHandle().c.lastIndexOf(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListTagListIterator<E>((ListIterator<NBTBase>)getHandle().c.listIterator(index));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object o) {
        if (o instanceof CanaryBaseTag) { // Pass handle, not actual object
            return getHandle().c.remove(((CanaryBaseTag)o).getHandle());
        }
        return getHandle().c.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E remove(int index) {
        return (E)CanaryBaseTag.wrap((NBTBase) getHandle().c.remove(index));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        return getHandle().c.removeAll(makeRaw(c));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return getHandle().c.retainAll(makeRaw(c));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {
        return (E)CanaryBaseTag.wrap((NBTBase) getHandle().c.set(index, ((CanaryBaseTag)element).getHandle())); // Need to pass handle, not element
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return getHandle().c();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        ArrayList<E> elements = new ArrayList<E>();
        for (Object base : getHandle().c) {
            elements.add((E)CanaryBaseTag.wrap((NBTBase)base));
        }
        return elements.subList(fromIndex, toIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] toArray() {
        return toArray(new Object[size()]);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        int size = size();

        if (a.length >= size) {
            for (int i = 0; i < size; i++) {
                a[i] = (T)get(i); // the get method wraps the original nbt tag
            }
            if (a.length > size) {
                a[size] = null; // as specified by the list javadoc
            }
            return a;
        }
        else {
            T[] arr = (T[])Array.newInstance(a.getClass(), size);

            for (int i = 0; i < size; i++) {
                arr[i] = (T)get(i); // the get method wraps the original nbt tag
            }
            return arr;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListTag<E> copy() {
        return new CanaryListTag<E>((NBTTagList)getHandle().b());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NBTTagList getHandle() {
        return (NBTTagList)tag;
    }
}
