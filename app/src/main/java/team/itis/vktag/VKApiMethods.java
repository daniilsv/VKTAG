package team.itis.vktag;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;


class VKApiMethods extends VKRequest.VKRequestListener {
    private static VKRequest request;
    private static VKApiMethods instance = null;

    private static VKApiMethods getInstance() {
        if (instance == null) instance = new VKApiMethods();
        return instance;
    }

    public static void addFriend(String friendId) {
        VKApiMethods.getId(true, friendId, new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    String id = (String) response.json.get("id");
                    request = VKApi.friends().add(VKParameters.from(
                            VKApiConst.USER_ID, id));
                    request.executeWithListener(getInstance());
                } catch (JSONException ignored) {
                }
            }
        });
    }

    public static void setLike(String type, int friendId, int item_id) {
        VKRequest request = new VKRequest("likes.add", VKParameters.from(
                "type", type,
                "owner_id", friendId,
                "item_id", item_id));
        request.executeWithListener(getInstance());
    }

    public static void repost(String type, String wallId, String itemId) {
        request = VKApi.wall().repost(VKParameters.from(
                "object", type + wallId + "_" + itemId));
        request.executeWithListener(getInstance());
    }

    public static void joinGroup(String groupId) {
        VKApiMethods.getId(false, groupId, new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    String id = (String) response.json.get("id");
                    request = VKApi.groups().join(VKParameters.from(
                            "group_id", id));
                    request.executeWithListener(getInstance());
                } catch (JSONException ignored) {
                }
            }
        });
    }

    public static void getId(boolean isUser, String id, VKRequest.VKRequestListener listener) {
        if (isUser) {
            request = VKApi.users().get(VKParameters.from(
                    "user_ids", id));
        } else {
            request = VKApi.groups().getById(VKParameters.from(
                    "group_id", id));
        }
        request.executeWithListener(listener);
    }


    @Override
    public void onComplete(VKResponse response) {
        System.out.println(response.responseString);
    }

    @Override
    public void onError(VKError error) {
        System.out.println(error.errorMessage);
    }
}
