/* eslint-disable no-undef */

const socket = new SockJS("http://localhost:8080/ws");
const stompClient = Stomp.over(socket);
stompClient.debug = function () {};

export default stompClient;
