import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Point;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by anand on 1/16/15.
 */
public class RTreeMaxDistanceTest {

    RTree<String, Point> rTree;

    @Before
    public void setup() {
        rTree = RTree.star().maxChildren(4).create();
    }

    @Test
    public void shouldNoRtReturnPoint() {
        addPoint("Chicago", Point.create(-87.629798, 41.878114));
        addPoint("Los Angeles", Point.create(-118.243685, 34.052234));
        addPoint("New York", Point.create(-74.005941, 40.712784));
        addPoint("SFO", Point.create(-122.419416, 37.774929));


        Iterable<Entry<String, Point>> entries = rTree.nearest(Point.create(-118.243685, 34.052234),6, 10).toBlocking().toIterable();
        entries.forEach(x -> {
            System.out.println(x.value());
        });

    }

    private void addPoint(String city, Point point) {
        rTree = rTree.add(new Entry<>(city, point));
    }
}
