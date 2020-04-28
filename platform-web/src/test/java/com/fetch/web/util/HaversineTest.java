package com.fetch.web.util;

import com.fetch.web.model.PostCode;
import org.junit.Assert;
import org.junit.Test;

/**
 * Use for testing
 * https://ukpostcodes.tenfourzero.net/?from=ab107jb&to=ab115qn
 */
public class HaversineTest {

    //AB10 6RN AB10 6RN
    @Test
    public void test() throws Exception {
        PostCode<String> p1 = new PostCode<>("AB10 1XG",57.144165160000000,-2.114847768000000);
        PostCode<String> p2 = new PostCode<>("AB10 6RN",57.137879760000000,-2.121486688000000);

        System.out.println(new Haversine().distance(p1, p2));
        Assert.assertEquals(0.5010765137289859, new Haversine().distance(p1, p2), 0.1);

        p1 = new PostCode<>("AB10 7JB",57.124273770000000,-2.127189644000000);
        p2 = new PostCode<>("AB11 5QN",57.142701090000000,-2.093295000000000);

        System.out.println(new Haversine().distance(p1, p2));
        Assert.assertEquals(1.80, new Haversine().distance(p1, p2), 0.1);

        p1 = new PostCode<>("YO8 9AR",53.776139995421300,-1.088288785754460);
        p2 = new PostCode<>("YO8 9AG",53.777136411753500,-1.082227612530020);

        System.out.println(new Haversine().distance(p1, p2));
        Assert.assertEquals(0.25, new Haversine().distance(p1, p2), 0.1);


    }
}