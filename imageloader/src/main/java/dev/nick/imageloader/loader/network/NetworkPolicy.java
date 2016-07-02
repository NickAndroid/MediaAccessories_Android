/*
 * Copyright (c) 2016 Nick Guo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.nick.imageloader.loader.network;

public class NetworkPolicy {

    public static final NetworkPolicy DEFAULT_NETWORK_POLICY = new NetworkPolicy.Builder()
            .onlyOnWifi(true)
            .build();

    boolean onlyOnWifi;

    private NetworkPolicy(boolean onlyOnWifi) {
        this.onlyOnWifi = onlyOnWifi;
    }

    public boolean isOnlyOnWifi() {
        return onlyOnWifi;
    }

    @Override
    public String toString() {
        return "NetworkPolicy{" +
                "onlyOnWifi=" + onlyOnWifi +
                '}';
    }

    public static class Builder {
        boolean onlyOnWifi;

        /**
         * @param onlyOnWifi {@code true to load image only under WIFI connection.}
         * @return Builder instance.
         */
        public Builder onlyOnWifi(boolean onlyOnWifi) {
            this.onlyOnWifi = onlyOnWifi;
            return Builder.this;
        }

        public NetworkPolicy build() {
            return new NetworkPolicy(onlyOnWifi);
        }
    }
}