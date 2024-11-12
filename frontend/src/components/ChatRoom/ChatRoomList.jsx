import axios, { AxiosError } from "axios";
import { Fragment, useState } from "react";

function ChatRoomList() {
  const [errorMessages, setErrorMessages] = useState([]);
  const [chatRooms, setChatRooms] = useState([]);
  const [isFirstTime, setIsFirstTime] = useState(true);

  const handleGetAllChatRoom = async function () {
    try {
      const token = localStorage.getItem("token");
      const { data } = await axios.get("/api/v1/chats", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setChatRooms(data);
      setErrorMessages([]);
      setIsFirstTime(false);
    } catch (err) {
      if (err instanceof AxiosError) {
        setErrorMessages(err.response.data.errors);
        setChatRooms([]);
      }
    }
  };

  return (
    <>
      <h2>Get All Chat Room</h2>
      <button onClick={handleGetAllChatRoom}>Get All Chat Room</button>

      {!isFirstTime && chatRooms.length === 0 && <p>No Chat Room found</p>}

      {chatRooms.length > 0 && (
        <ul>
          {chatRooms.map((chatRoom) => (
            <Fragment key={chatRoom.id}>
              <li>ID: {chatRoom.id}</li>
              <li>
                Owner IDs:
                <ul>
                  {chatRoom.ownerIds.map((id) => (
                    <li key={id}>{id}</li>
                  ))}
                </ul>
              </li>
              <br />
            </Fragment>
          ))}
        </ul>
      )}

      {errorMessages.length > 0 && (
        <ul>
          {errorMessages.map((err, i) => (
            <li key={i}>{err}</li>
          ))}
        </ul>
      )}
    </>
  );
}

export default ChatRoomList;
