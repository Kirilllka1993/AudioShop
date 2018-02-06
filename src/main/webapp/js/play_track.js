/**
 *
 */

function playTrack (trackId, trackLocation) {
    $("audio source").attr("src", "s?command=load_track&trackId="+trackId+"&location="+trackLocation);
    var player = $("#player")[0];
    player.load();
    player.play();
}