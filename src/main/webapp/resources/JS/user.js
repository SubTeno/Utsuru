function edit(obj) {
  $("#editframe").attr(
    "src",
    "/home/user/edit/" + $(obj).data("anime-id") + ""
  );
}
