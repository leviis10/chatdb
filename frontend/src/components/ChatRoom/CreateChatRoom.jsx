import axios, { AxiosError } from "axios";
import { useRef, useState } from "react";

function CreateChatRoom() {
  const [errorMessages, setErrorMessages] = useState([]);
  const [successMessages, setSuccessMessages] = useState("");
  const receiverIdInputRef = useRef(null);

  const handleCreateChatRoom = async function (e) {
    e.preventDefault();
    const token = localStorage.getItem("token");

    try {
      await axios.post(
        "/api/v1/chats",
        { receiverId: receiverIdInputRef.current.value },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setSuccessMessages("Created");
      setErrorMessages([]);
      localStorage.setItem("receiverId", receiverIdInputRef.current.value);
      receiverIdInputRef.current.value = "";
    } catch (err) {
      if (err instanceof AxiosError) {
        setErrorMessages(err.response.data.errors);
        setSuccessMessages("");
      }
    }
  };

  return (
    <>
      <h2>Create / Connect Chat Room</h2>

      <form onSubmit={handleCreateChatRoom}>
        <div>
          <label htmlFor="receiverId">Receiver ID</label>{" "}
          <input
            type="number"
            min="0"
            id="receiverId"
            ref={receiverIdInputRef}
          />
        </div>

        <div>
          <button type="submit">Create / Connect Chat Room</button>
        </div>
      </form>

      {successMessages && <p>{successMessages}</p>}

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

export default CreateChatRoom;
