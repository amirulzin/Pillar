package com.baseconfig.pillar.databinding.recycler;

import java.util.ArrayList;
import java.util.List;

/**
 * Useful for "flatting" collection to be used in RecyclerViews. For simple use case, this is enough to implement "sectioning" in RecyclerView
 *
 * @param <Group>    Main group, e.g Classroom
 * @param <Subgroup> Subgroup that is contained within the main Group e.g. Student and must be provided as a {@link List} collection
 */
public class FlatData<Group, Subgroup> {
    private final Group group;
    private final Subgroup subgroup;
    private final Factory<Group, Subgroup> factory;

    public FlatData(Group group, Subgroup subgroup, Factory<Group, Subgroup> factory) {
        this.group = group;
        this.subgroup = subgroup;
        this.factory = factory;
    }

    /**
     * Create a composite collection of FlatData. Headers are included.
     * By iterating the returned list, headers can be checked if {@link FlatData#getSubgroup()} is null.
     *
     * @param list       Original list of main Group type
     * @param factory    Factory for passing values from a single Group type to Subgroups
     * @param <Group>    Main Group type see {@link FlatData}
     * @param <Subgroup> Subgroup type that is included within main Group type
     * @return A composite list of collection.
     */
    public static <Group, Subgroup> List<FlatData<Group, Subgroup>> getCollection(List<Group> list, Factory<Group, Subgroup> factory) {

        ArrayList<FlatData<Group, Subgroup>> outList = new ArrayList<>();
        for (Group g : list) {

            outList.add(new FlatData<>(g, null, factory)); // for headers
            List<Subgroup> subList = factory.getSubgroupList(g);
            for (Subgroup s : subList) {
                outList.add(new FlatData<>(g, s, factory));
            }
        }
        return outList;
    }

    public boolean isHeader() {
        return subgroup == null;
    }

    public String getName() {
        return factory.getName(group);
    }

    public Group getGroup() {
        return group;
    }

    public Subgroup getSubgroup() {
        return subgroup;
    }

    public interface Factory<Group, Subgroup> {
        String getName(Group group);

        List<Subgroup> getSubgroupList(Group group);
    }
}
