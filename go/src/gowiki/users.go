package main

import (
    "fmt"
    "net/http"
    "encoding/json"
)

type User struct {
    Id int
    Name string
    Email string
    password string //not visible (first char lowercase)
}

//global variable - array with 3 users
var users [3]User

func handler(w http.ResponseWriter, r *http.Request) {
    b, _ := json.Marshal(users)
    fmt.Fprintf(w, string(b))
}

func main() {
    users[0] = User{1, "joe", "joe@yahoo.com", "xyz"}
    users[1] = User{2, "mike", "mike@gmail.com", "xyz"}
    users[2] = User{3, "frank", "frank@email.com", "xyz"}

    fmt.Printf("Server running at http://localhost:8086/users \n")
    http.HandleFunc("/users", handler) //add handler for path /users
    http.ListenAndServe(":8086", nil) //start server
}
