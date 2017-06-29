package common.android.util.databinding.recycler;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.ListIterator;

/**
 * RecyclerView adapter for a list of T objects where its layouts implemented databinding. <br/>
 * Example #1: <br/>
 * Given a list of Student objects to be displayed in a recycler view, the adapter below is created:
 * <pre>{@code
 * public class MyAdapter extends BindingItemAdapter<Student> {
 *      ...
 *      @Override
 *      public BindingHolder<? extends ViewDataBinding> onCreateBindingHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
 *          return new BindingHolder<>(DataBindingUtil.inflate(inflater, viewType,  R.layout.my_list_item_layout, false));
 *      }
 *
 *      @Override
 *      public void onBind(BindingHolder<? extends ViewDataBinding> holder, Student item, int position) {
 *          ViewDataBinding binding = holder.getBinding();
 *          if (binding instanceof MyListItemLayoutBinding) { //MyListItemLayoutBinding is generated by the databinding library
 *              MyListItemLayoutBinding b = ((ModuleListItemHeaderBinding) binding);
 *              b.titleTextView.setText(item.getStudentName());
 *          }
 *      }
 * }
 * }</pre>
 * Example #2 (complex): <br/>
 * 1. You have multiple view types for list item (section headers, and actual list item) <br/>
 * 2. You have to display a list of Classroom where each Classroom object contains a list of Students (sectioning) <br/>
 * <pre>{@code
 * public class MyActivity extends AppCompatActivity{
 *      @Override
 *      public void onCreate(Bundle onSaveInstanceState){
 *          ...
 *          List<Classroom> classrooms = retrieveClassrooms(); //sample collection
 *          RecyclerView rv = binding.recyclerView; //or (RecyclerView) findViewById(R.id.recyclerView) etc.
 *          rv.setAdapter(new Adapter(FlatData.getCollection(classrooms, new FlatData.Factory<Classroom, Student>() {
 *              @Override
 *              public String getName(Classroom group) {
 *              return group.getClassroomName();
 *              }
 *
 *              @Override
 *              public List<Student> getSubgroupList(Classroom classroom) {
 *              return classroom.getStudents();
 *              }
 *          })));
 *          ...
 *      }
 * }
 *
 * public class MyAdapter extends BindingItemAdapter<FlatData<Classroom, Student>> {
 *      ...
 *      @Override
 *      public int getItemViewType(int position) {
 *          // isHeader() method is provided by {@link FlatData} class
 *          return getList().get(position).isHeader() ? R.layout.list_header_classroom : R.layout.list_item_student;
 *      }
 *
 *      @Override
 *      public BindingHolder<? extends ViewDataBinding> onCreateBindingHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
 *          // notice we directly use the viewType as suggested by the official support library
 *          return new BindingHolder<>(DataBindingUtil.inflate(inflater, viewType, viewType, false));
 *      }
 *
 *      @Override
 *      public void onBind(BindingHolder<? extends ViewDataBinding> holder, FlatData<Classroom, Student> item, int position) {
 *          ViewDataBinding binding = holder.getBinding();
 *          if (binding instanceof ListHeaderClassroomBinding) {
 *              ListHeaderClassroomBinding b = (ListHeaderClassroomBinding) binding;
 *              b.headerTextView.setText(item.getName()); //getName() method is provided by {@link FlatData} class
 *          }
 *          else if(binding instanceof ListItemStudentBinding){
 *              ListItemStudentBinding b = (ListItemStudentBinding) binding;
 *              b.studentNameTextView.setText(item.getSubgroup().getStudentName()); //getSubgroup() method is provided by {@link FlatData} class
 *
 *              // FlatData just reference the original dataset. Refer {@link FlatData} for more details
 *          }
 *      }
 * }
 * }</pre>
 * <p>
 * <p>
 * Simple list methods are also provided but feel free to roll your own implementation if you have a specialized list.
 */

public abstract class BindingItemAdapter<T> extends RecyclerView.Adapter<BindingHolder<? extends ViewDataBinding>> {

    private final List<T> collection;

    public BindingItemAdapter(final List<T> collection) {
        this.collection = collection;
    }

    public List<T> getCollection() {
        return collection;
    }

    @Override
    public BindingHolder<? extends ViewDataBinding> onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return onCreateBindingHolder(LayoutInflater.from(parent.getContext()), parent, viewType);
    }

    public abstract BindingHolder<? extends ViewDataBinding> onCreateBindingHolder(LayoutInflater inflater, ViewGroup parent, final int viewType);

    @Override
    public void onBindViewHolder(final BindingHolder<? extends ViewDataBinding> holder, final int position) {
        onBind(holder, collection.get(position), position);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public abstract void onBind(final BindingHolder<? extends ViewDataBinding> holder, final T item, final int position);

    /**
     * Remove item from the list
     */
    public void removeItem(@NonNull T t) {
        ListIterator<T> iterator = getCollection().listIterator();
        while (iterator.hasNext()) {
            int i = iterator.nextIndex();
            T next = iterator.next();
            if (t.equals(next)) {
                iterator.remove();
                notifyItemRemoved(i);
                break;
            }
        }
    }

    /**
     * Add item to the last of the list
     */
    public void addItem(T t) {
        int size = getCollection().size();
        getCollection().add(t);
        notifyItemInserted(size);
    }

    /**
     * A helper method to get an item from base collection where the predicate matches or null if item not found.
     * The usage of this method is entirely optional and are not used within the library.
     *
     * @return A {@link Pair} of T object (left) to its index location (right) or null if pair if item not found
     */
    @Nullable
    public Pair<T, Integer> getItem(ListPredicate<T> predicate) {
        List<T> collection = getCollection();
        for (int i = 0; i < collection.size(); i++) {
            T t = collection.get(i);
            if (predicate.apply(t)) {
                return new Pair<>(t, i);
            }
        }
        return null;
    }

    /**
     * Notify item changed for the given item
     */
    public void notifyItemChanged(T t) {
        notifyItemChanged(collection.indexOf(t));
    }

    /**
     * Update the backing list with new list of a probably similar objects.
     * This uses {@link DiffUtil} in the implementation
     *
     * @param list New list that is separate from the original backing list.
     */
    public void updateList(final List<T> list, final ListPredicate<T> compare) {
        DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return getCollection().size();
            }

            @Override
            public int getNewListSize() {
                return list.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

                return compare.isSimilar(getCollection().get(oldItemPosition), list.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return compare.isEquals(getCollection().get(oldItemPosition), list.get(newItemPosition));
            }
        }).dispatchUpdatesTo(this);
    }

    /**
     * A Predicate can determine a true or false value for any input of its
     * parameterized type. For example, a {@code RegexPredicate} might implement
     * {@code Predicate<String>}, and return true for any String that matches its
     * given regular expression.
     * <p>Note: This documentation is sourced from com.android.internal package introduced in API 24</p>
     * <p>
     * <p>{@code ListPredicate<T>} pretty much is something similar in the same vein but provide
     * interface specific to RecyclerView adapters.</p>
     */
    public interface ListPredicate<T> {

        /**
         * Used in {@link #getItem(ListPredicate)} where this should return true
         * if the object matches by id, name, or Object.equals() and etc. in its implementation.
         * <p>
         * If {@link #getItem(ListPredicate)} is not used, no need to implement this.
         */
        boolean apply(T t);

        /**
         * {@link DiffUtil} will use this to decide whether item are the same (id, name, color, etc.)
         * <p>You can refer to <a href="https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/ui/ProductAdapter.java#L61-L63">this official Android github example</a></p>
         *
         * @param oldObject Old object passed from original list
         * @param newObject 'Updated' object passed from a new list
         * @return true if similar.
         */
        boolean isSimilar(T oldObject, T newObject);

        /**
         * {@link DiffUtil} use this to decide whether item are the equals
         * (make sure to use a variant of Object.equals() in implementation of this method)
         * <p>
         * <p>You can refer to <a href="https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/ui/ProductAdapter.java#L67-L73">this official Android github example</a></p>
         *
         * @param oldObject Old object passed from original list
         * @param newObject 'Updated' object passed from a new list
         * @return true if equals.
         */
        boolean isEquals(T oldObject, T newObject);
    }
}
