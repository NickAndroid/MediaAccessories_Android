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

package dev.nick.twenty;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nick.scalpel.Scalpel;
import com.nick.scalpel.annotation.binding.FindView;
import com.nick.scalpel.annotation.request.RequirePermission;

import java.util.ArrayList;
import java.util.List;

import dev.nick.imageloader.DisplayListener;
import dev.nick.imageloader.ImageLoader;
import dev.nick.imageloader.LoadingListener;
import dev.nick.imageloader.display.DisplayOption;
import dev.nick.imageloader.display.ImageQuality;
import dev.nick.imageloader.display.animator.FadeInImageAnimator;
import dev.nick.imageloader.loader.result.BitmapResult;
import dev.nick.imageloader.loader.result.Cause;
import dev.nick.logger.LoggerManager;

@RequirePermission(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET})
public class LoadImageTest extends BaseTest {

    @FindView(id = R.id.list)
    ListView listView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_image_layout);
        setTitle(getClass().getSimpleName());
        Scalpel.getInstance().wire(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final List<Track> tracks = getTrackList();

        final BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return tracks.size();
            }

            @Override
            public Object getItem(int position) {
                return tracks.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, final ViewGroup parent) {

                final ViewHolder holder;

                if (convertView == null) {
                    convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.net_image_layout, parent, false);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                String uri = tracks.get(position).getUrl();

                holder.progressBar.setProgress(0);
                holder.textView.setText("");

                ImageLoader
                        .getInstance()
                        .loadImage(uri, new LoadingListener() {
                                    @Override
                                    public void onError(@NonNull Cause cause) {
                                        LoggerManager.getLogger(getClass()).error(cause);
                                        holder.textView.setText("Error");
                                    }

                                    @Override
                                    public void onComplete(@Nullable BitmapResult result) {
                                        if (result != null) {
                                            holder.progressBar.setProgress((int) (1 * 100));
                                            LoggerManager.getLogger(getClass()).debug("onComplete:" + result.result);
                                            holder.textView.setText("Completed");
                                        }
                                        holder.imageView.setImageBitmap(result != null ? result.result : null);
                                    }

                                    @Override
                                    public void onProgressUpdate(float progress) {
                                        holder.progressBar.setProgress((int) (progress * 100));
                                        holder.textView.setText("" + (int) (progress * 100));
                                    }

                                    @Override
                                    public void onCancel() {
                                        LoggerManager.getLogger(getClass()).debug("onCancel");
                                        holder.progressBar.setProgress(0);
                                        holder.textView.setText("Canceled");
                                    }

                                    @Override
                                    public void onStartLoading() {
                                        holder.textView.setText("Start");
                                        holder.progressBar.setProgress(0);
                                    }
                                });

                return convertView;
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Track track = (Track) adapter.getItem(i);
                ImageLoader.getInstance().cancel(track.getUrl());
            }
        });
    }

    final String[] urls = new String[]{
            "http://i.imgur.com/ZXVlev9.jpg",
            "http://i.imgur.com/LT6RmQU.png",
            "http://i.imgur.com/8w0hWDS.jpg",
            "http://i.imgur.com/wCbQpOr.jpg",
            "http://i.imgur.com/rUcXDip.jpg",
            "http://i.imgur.com/bzuhIg4.png",
            "http://i.imgur.com/LsEW9kS.png",
            "http://i.imgur.com/MyAcXe5.png",
            "http://i.imgur.com/PwErqAf.png",
            "http://i.imgur.com/jz1zgXU.png",
            "http://i.imgur.com/8PQ43ov.jpg",
            "http://i.imgur.com/vxAIMJt.png",
            "http://i.imgur.com/ZXVlev9.jpg",
            "http://i.imgur.com/LT6RmQU.png",
            "http://i.imgur.com/8w0hWDS.jpg",
            "http://i.imgur.com/wCbQpOr.jpg",
            "http://i.imgur.com/LsEW9kS.png",
            "http://i.imgur.com/MyAcXe5.png",
            "http://i.imgur.com/PwErqAf.png",
            "http://i.imgur.com/jz1zgXU.png",
            "http://i.imgur.com/moer0PI.jpg",
            "http://i.imgur.com/vRUz3TD.jpg",
            "http://i.imgur.com/ZXVlev9.jpg",
            "http://i.imgur.com/LT6RmQU.png",
            "http://i.imgur.com/8w0hWDS.jpg",
            "http://i.imgur.com/wCbQpOr.jpg",
            "http://i.imgur.com/rUcXDip.jpg",
            "http://i.imgur.com/bzuhIg4.png",
            "http://i.imgur.com/LsEW9kS.png",
            "http://i.imgur.com/MyAcXe5.png",
            "http://i.imgur.com/PwErqAf.png",
            "http://i.imgur.com/jz1zgXU.png",
            "http://i.imgur.com/8PQ43ov.jpg",
            "http://i.imgur.com/vxAIMJt.png",
            "http://i.imgur.com/ZXVlev9.jpg",
            "http://i.imgur.com/LT6RmQU.png",
            "http://i.imgur.com/8w0hWDS.jpg",
            "http://i.imgur.com/wCbQpOr.jpg",
            "http://i.imgur.com/LsEW9kS.png",
            "http://i.imgur.com/MyAcXe5.png",
            "http://i.imgur.com/PwErqAf.png",
            "http://i.imgur.com/jz1zgXU.png",
            "http://i.imgur.com/moer0PI.jpg",
            "http://i.imgur.com/vRUz3TD.jpg",
            "http://i.imgur.com/ZXVlev9.jpg",
            "http://i.imgur.com/LT6RmQU.png",
            "http://i.imgur.com/8w0hWDS.jpg",
            "http://i.imgur.com/wCbQpOr.jpg",
            "http://i.imgur.com/rUcXDip.jpg",
            "http://i.imgur.com/bzuhIg4.png",
            "http://i.imgur.com/LsEW9kS.png",
            "http://i.imgur.com/MyAcXe5.png",
            "http://i.imgur.com/PwErqAf.png",
            "http://i.imgur.com/jz1zgXU.png",
            "http://i.imgur.com/8PQ43ov.jpg",
            "http://i.imgur.com/vxAIMJt.png",
            "http://i.imgur.com/ZXVlev9.jpg",
            "http://i.imgur.com/LT6RmQU.png",
            "http://i.imgur.com/8w0hWDS.jpg",
            "http://i.imgur.com/wCbQpOr.jpg",
            "http://i.imgur.com/LsEW9kS.png",
            "http://i.imgur.com/MyAcXe5.png",
            "http://i.imgur.com/PwErqAf.png",
            "http://i.imgur.com/jz1zgXU.png",
            "http://i.imgur.com/moer0PI.jpg",
            "http://i.imgur.com/vRUz3TD.jpg",
            "http://i.imgur.com/ZXVlev9.jpg",
            "http://i.imgur.com/LT6RmQU.png",
            "http://i.imgur.com/8w0hWDS.jpg",
            "http://i.imgur.com/wCbQpOr.jpg",
            "http://i.imgur.com/rUcXDip.jpg",
            "http://i.imgur.com/bzuhIg4.png",
            "http://i.imgur.com/LsEW9kS.png",
            "http://i.imgur.com/MyAcXe5.png",
            "http://i.imgur.com/PwErqAf.png",
            "http://i.imgur.com/jz1zgXU.png",
            "http://i.imgur.com/8PQ43ov.jpg",
            "http://i.imgur.com/vxAIMJt.png",
            "http://i.imgur.com/ZXVlev9.jpg",
            "http://i.imgur.com/LT6RmQU.png",
            "http://i.imgur.com/8w0hWDS.jpg",
            "http://i.imgur.com/wCbQpOr.jpg",
            "http://i.imgur.com/LsEW9kS.png",
            "http://i.imgur.com/MyAcXe5.png",
            "http://i.imgur.com/PwErqAf.png",
            "http://i.imgur.com/jz1zgXU.png",
            "http://i.imgur.com/moer0PI.jpg",
            "http://i.imgur.com/vRUz3TD.jpg",
            "http://i.imgur.com/ZXVlev9.jpg",
            "http://i.imgur.com/LT6RmQU.png",
            "http://i.imgur.com/8w0hWDS.jpg",
            "http://i.imgur.com/wCbQpOr.jpg",
            "http://i.imgur.com/rUcXDip.jpg",
            "http://i.imgur.com/bzuhIg4.png",
            "http://i.imgur.com/LsEW9kS.png",
            "http://i.imgur.com/MyAcXe5.png",
            "http://i.imgur.com/PwErqAf.png",
            "http://i.imgur.com/jz1zgXU.png",
            "http://i.imgur.com/8PQ43ov.jpg",
            "http://i.imgur.com/vxAIMJt.png",
            "http://i.imgur.com/ZXVlev9.jpg",
            "http://i.imgur.com/LT6RmQU.png",
            "http://i.imgur.com/8w0hWDS.jpg",
            "http://i.imgur.com/wCbQpOr.jpg",
            "http://i.imgur.com/LsEW9kS.png",
            "http://i.imgur.com/MyAcXe5.png",
            "http://i.imgur.com/PwErqAf.png",
            "http://i.imgur.com/jz1zgXU.png",
            "http://i.imgur.com/moer0PI.jpg",
            "http://i.imgur.com/vRUz3TD.jpg"
    };

    List<Track> getTrackList() {
        List<Track> out = new ArrayList<>(urls.length);
        for (String s : urls) {
            Track t = new Track();
            t.setUrl(s);
            t.setTitle(s);
            out.add(t);
        }
        return out;
    }

    class ViewHolder {
        @FindView(id = R.id.image)
        ImageView imageView;
        @FindView(id = R.id.progressBar)
        ProgressBar progressBar;
        @FindView(id = R.id.textView)
        TextView textView;

        public ViewHolder(View convert) {
            Scalpel.getInstance().wire(convert, this);
        }
    }
}