/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.flamegraph.web;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sample.flamegraph.domain.Customer;
import sample.flamegraph.domain.CustomerRepository;

@Controller
@RequestMapping("/icicle")
public class IcicleController {

    @RequestMapping(method = RequestMethod.GET, value = "/endpoint/1/{n}")
    @ResponseBody
    int firstEndpoint(@PathVariable int n) {
        return doNothing(n % 4, n);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/endpoint/2/{n}")
    @ResponseBody
    int secondEndpoint(@PathVariable int n) {
        return doNothing(n % 4, n);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/endpoint/3/{n}")
    @ResponseBody
    int thirdEndpoint(@PathVariable int n) {
        return doNothing(n % 4, n);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/endpoint/4/{n}")
    @ResponseBody
    int fourthEndpoint(@PathVariable int n) {
        return doNothing(n % 4, n);
    }

    volatile int[] cpt = new int[4];

    public int doNothing(int index, int n) {

        for (int i = 0; i < 1_000_000 + n; i++) {
            if (i % 3 == 0) {
                cpt[index] = 138783;
            } else if (i % 2 == 0) {
                cpt[index] = 187383;
            } else if (i % 4 == 0) {
                cpt[index] = 18718;
            } else if (i % 5 == 0) {
                cpt[index] = 1871;
            } else if (i % 6 == 0) {
                cpt[index] = 93289;
            } else if (i % 7 == 0) {
                cpt[index] = 12;
            } else if (i % 13 == 0) {
                cpt[index] = 167;
            } else if (i % 21 == 0) {
                cpt[index] = 6732;
            } else if (i % 27 == 0) {
                cpt[index] = 8989;
            } else if (i % 33 == 0) {
                cpt[index] = 1132;
            } else if (i % 213 == 0) {
                cpt[index] = 56;
            } else if (i % 2213 == 0) {
                cpt[index] = 99;
            } else if (i % 17 == 0) {
                cpt[index] = 13;
            } else if (i % 217 == 0) {
                cpt[index] = 23;
            } else if (i % 317 == 0) {
                cpt[index] = 76;
            } else if (i % 117 == 0) {
                cpt[index] = 44;
            } else if (i % 3147 == 0) {
                cpt[index] = 12;
            } else if (i % 517 == 0) {
                cpt[index] = 8;
            } else if (i % 5 == 0) {
                cpt[index] = 1871;
            } else if (i % 6 == 0) {
                cpt[index] = 93289;
            } else if (i % 7 == 0) {
                cpt[index] = 12;
            } else if (i % 13 == 0) {
                cpt[index] = 167;
            } else if (i % 21 == 0) {
                cpt[index] = 6732;
            } else if (i % 27 == 0) {
                cpt[index] = 8989;
            } else if (i % 33 == 0) {
                cpt[index] = 1132;
            } else if (i % 213 == 0) {
                cpt[index] = 56;
            } else if (i % 2213 == 0) {
                cpt[index] = 99;
            } else if (i % 17 == 0) {
                cpt[index] = 13;
            } else if (i % 217 == 0) {
                cpt[index] = 23;
            } else if (i % 317 == 0) {
                cpt[index] = 76;
            } else if (i % 117 == 0) {
                cpt[index] = 44;
            } else if (i % 3147 == 0) {
                cpt[index] = 12;
            } else if (i % 517 == 0) {
                cpt[index] = 8;
            } else if (i % 22317 == 0) {
                cpt[index] = 276;
            } else if (i % 22117 == 0) {
                cpt[index] = 244;
            } else if (i % 223147 == 0) {
                cpt[index] = 212;
            } else if (i % 22517 == 0) {
                cpt[index] = 28;
            } else if (i % 225 == 0) {
                cpt[index] = 21871;
            } else if (i % 226 == 0) {
                cpt[index] = 293289;
            } else if (i % 227 == 0) {
                cpt[index] = 212;
            } else if (i % 2213 == 0) {
                cpt[index] = 2167;
            } else if (i % 2221 == 0) {
                cpt[index] = 26732;
            } else if (i % 2227 == 0) {
                cpt[index] = 28989;
            } else if (i % 2233 == 0) {
                cpt[index] = 21132;
            } else if (i % 22213 == 0) {
                cpt[index] = 256;
            } else if (i % 222213 == 0) {
                cpt[index] = 299;
            } else if (i % 2217 == 0) {
                cpt[index] = 213;
            } else if (i % 22217 == 0) {
                cpt[index] = 223;
            } else if (i % 22317 == 0) {
                cpt[index] = 276;
            } else if (i % 22117 == 0) {
                cpt[index] = 244;
            } else if (i % 223147 == 0) {
                cpt[index] = 212;
            } else if (i % 22517 == 0) {
                cpt[index] = 28;
            } else {
                cpt[index] = -1;
            }
        }
        return cpt[index];
    }
}
