import { useRef } from "react";
import stompClient from "../utils/stompClient";

function Chat() {
  const roomIdInputRef = useRef(null);
  const contentInputRef = useRef(null);
  const loggedInId = localStorage.getItem("id");
  const receiverId = localStorage.getItem("receiverId");

  const handleSendMessage = function (e) {
    e.preventDefault();

    const chatPayload = {
      senderId: +loggedInId,
      receiverId: +receiverId,
      content: contentInputRef.current.value,
      chatRoomId: roomIdInputRef.current.value,
    };

    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatPayload));

    console.log(`${loggedInId}: ${contentInputRef.current.value}`);

    contentInputRef.current.value = "";
  };

  return (
    <>
      <h2>Start Chatting</h2>

      <form onSubmit={handleSendMessage}>
        <div>
          <label htmlFor="roomId">Room ID</label>{" "}
          <input type="number" id="roomId" ref={roomIdInputRef} />
        </div>

        <div>
          <label htmlFor="content">Content</label>{" "}
          <input type="text" id="content" ref={contentInputRef} />
        </div>

        <div>
          <button type="submit">Send</button>
        </div>
      </form>
    </>
  );
}

export default Chat;
