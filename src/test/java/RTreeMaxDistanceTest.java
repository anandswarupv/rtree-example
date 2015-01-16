import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Point;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by anand on 1/16/15.
 */
public class RTreeMaxDistanceTest {

    RTree<String, Point> rTree;

    @Before
    public void setup() {
        rTree = RTree.star().maxChildren(4).create();
        addPoint("Chicago", Point.create(-87.629798, 41.878114));
        addPoint("Los Angeles", Point.create(-118.243685, 34.052234));
        addPoint("New York", Point.create(-74.005941, 40.712784));
        addPoint("SFO", Point.create(-122.419416, 37.774929));
    }

    @Test
    public void shouldReturnOnlyOnePoint() {
        List<String> cities = getCitiesInDistanceRange(Point.create(-118.243685, 34.052234), 5);
        Assert.assertEquals(cities.size(), 1);
    }

    @Test
    public void shouldReturnTwoPoints() {
        List<String> cities = getCitiesInDistanceRange(Point.create(-118.243685, 34.052234), 6);
        Assert.assertTrue(cities.size() == 2);
    }

    @Test
    public void shouldReturnThreePoints() {
        List<String> cities = getCitiesInDistanceRange(Point.create(-118.243685, 34.052234), 32);
        Assert.assertTrue(cities.size() == 3);
    }

    @Test
    public void shouldReturnFourPoints() {
        List<String> cities = getCitiesInDistanceRange(Point.create(-118.243685, 34.052234), 60);
        Assert.assertTrue(cities.size() == 4);
    }

    private List<String> getCitiesInDistanceRange(Point point, Integer distanceIn100Kms) {
        List<String> cities = Lists.newArrayList();
        Iterable<Entry<String, Point>> entries = rTree.nearest(point, distanceIn100Kms, 10).toBlocking().toIterable();
        entries.forEach(x -> {
            cities.add(x.value());
        });
        return cities;
    }

    private void addPoint(String city, Point point) {
        rTree = rTree.add(new Entry<>(city, point));
    }
}
