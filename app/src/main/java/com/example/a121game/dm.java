package com.example.a121game;

public class dm {

        private class Node{
            // Fields
            int data;
            Node next;
            Node previous;

            // Constructor
            Node(int data) { this.data = data; next = null; previous = null; }

            // toString():  overrides Object's toString() method
            public String toString() {
                return String.valueOf(data);
            }
        }

        // Fields
        private Node front;
        private Node back;
        private Node cursor;
        private int length;
        private int cursorLocation;

        // Constructor
        dm() {
            front = back = cursor = null;
            length = 0;
            cursorLocation = -1;
        }


        // Access Functions --------------------------------------------------------

        // isEmpty()
        // Returns true if this List is empty, false otherwise.
        boolean isEmpty() {
            return length == 0;
        }

        // length()
        // Returns length of this List.
        int length() {
            return length;
        }

        // front()
        // Returns front element.
        // Pre: !this.isEmpty()
        int front(){
            if( this.isEmpty() ){
                throw new RuntimeException(
                        "List Error: front() called on empty List");
            }
            return front.data;
        }

        // back()
        // Returns back element.
        // Pre: !this.isEmpty()
        int back(){
            if ( this.isEmpty() ){
                throw new RuntimeException(
                        "List Error: back() called on empty List");
            }
            return back.data;
        }

        // index()
        // If cursor is defined, returns the index of the cursor element,
        // otherwise returns -1.
        int index(){
            if ( cursor == null ){
                return -1;
            }
            else {
                return cursorLocation;
            }
        }

        // get()
        // Returns cursor element.
        // Pre: length()>0, index()>=0
        int get(){
            if ( this.isEmpty()){
                throw new RuntimeException(
                        "List Error: get() called on empty List");
            }
            if ( index() == -1 ){
                throw new RuntimeException(
                        "List Error: get() called on Null Cursor.");
            }

            return this.cursor.data;
        }

        // Manipulation Procedures -------------------------------------------------

        // moveNext()
        // If cursor is defined and not at back, moves cursor one step toward
        // back of this List, if cursor is defined and at back, cursor becomes
        // undefined, if cursor is undefined does nothing.
        void moveNext(){
            if ( cursorLocation >= 0 ){
                this.cursor = this.cursor.next;
                cursorLocation++;
            }
        }

        // movePrev()
        // If cursor is defined and not at front, moves cursor one step toward
        // front of this List, if cursor is defined and at front, cursor becomes
        // undefined, if cursor is undefined does nothing.
        void movePrev(){
            if( cursorLocation != -1 ){
                this.cursor = this.cursor.previous;
                cursorLocation--;
            }
        }

        // moveFront()
        // If List is non-empty, places the cursor under the front element,
        // otherwise does nothing.
        void moveFront(){
            if (this.isEmpty()){
                throw new RuntimeException(
                        "List error: moveFront() called empty List");
            }

            this.cursor = this.front;
            cursorLocation = 0;
        }

        // moveBack()
        // If List is non-empty, places the cursor under the back element,
        // otherwise does nothing.
        void moveBack(){
            if ( this.isEmpty() ){
                throw new RuntimeException(
                        "List error: moveBack() called empty List");
            }
            this.cursor = this.back;
            cursorLocation = length - 1;
        }

        // clear()
        // Resets this List to its original empty state.
        void clear(){
            int loop = this.length;
            for(int i = 0; i < loop; i++){
                this.deleteFront();
            }
        }


        // append(int data)
        // appends data to back of this List.
        // Pre: If List is non-empty
        void append(int data){
            Node N = new Node(data);
            if( this.isEmpty() ) {
                front = back = cursor = N;
            }else{
                N.previous =  back;
                back.next = N;
                back = N;
                N.next = null;
            }
            length++;
        }

        // prepend(int data)
        // Insert new element into this List.
        // insertion takes place before front element.
        // Pre: If List is non-empty
        void prepend(int data){
            Node N = new Node(data);
            if( this.isEmpty() ) {
                front = back = cursor = N;
            }else{
                N.next = front;
                front.previous = N;
                front = N;
                N.previous = null;
            }
            if( cursorLocation >= 0 ){
                cursorLocation++;
            }

            length++;
        }


        // insertBefore(int data)
        // Insert new element before cursor.
        // Pre: length()>0, index()>=0
        void insertBefore(int data){
            if(this.isEmpty()){
                throw new RuntimeException(
                        "List Error: insertBefore() called on empty List.");
            }
            if( cursorLocation == -1){
                throw new RuntimeException(
                        "List Error: insertBefore() cursor is undefined (-1)");
            }
            if( cursor == front ){
                prepend(data);
            }
            else{
                Node N = new Node(data);
                cursor.previous.next = N;
                N.previous = cursor.previous;
                N.next = cursor;
                cursor.previous = N;
                length++;
                cursorLocation++;
            }

        }
        // insertAfter(int data)
        // Inserts new element after cursor.
        // Pre: length()>0, index()>=0
        void insertAfter(int data){
            if(this.isEmpty()){
                throw new RuntimeException(
                        "List Error: insertAfter() called on empty List.");
            }
            if( cursorLocation == -1){
                throw new RuntimeException(
                        "List Error: insertAfter() cursor is undefined (-1)");
            }
            if(cursor == back){
                append(data);
            }
            else{
                Node N = new Node(data);
                cursor.next.previous = N;
                N.previous = cursor;
                N.next = cursor.next;
                cursor.next = N;
                length++;
            }
        }
        // deleteFront()
        // Deletes front element from this List.
        // Pre: !this.isEmpty()
        void deleteFront(){
            if(this.isEmpty()){
                throw new RuntimeException(
                        "List Error: deleteFront() called on empty List");
            }
            if( this.length > 1 ){

                if( cursor != front){
                    front = front.next;
                    front.previous = null;
                    cursorLocation--;
                    length--;
                }
                else{
                    front = front.next;
                    front.previous = null;
                    cursor = null;
                    length--;
                }

            }else{
                front = back = cursor =  null;
                length--;
                cursorLocation--;
            }

        }

        // deleteBack()
        // Deletes the back element.
        // Pre: length()>0
        void deleteBack(){
            if(this.isEmpty()){
                throw new RuntimeException(
                        "List Error: deleteBack() called on empty List");
            }
            if(this.length>1){

                if( cursor == back ){
                    cursor = null;
                }

                back = back.previous;
                back.next = null;

            }else{
                front = back = null;
            }

            length--;
        }

        // delete()
        // Deletes cursor element, making cursor undefined.
        // Pre: length()>0, index()>=0
        void delete(){
            if(this.isEmpty()){
                throw new RuntimeException(
                        "List Error: delete() called on empty List");
            }
            if (index()== -1){
                throw new RuntimeException(
                        "List Error: delete() called with no index (-1)");
            }

            if( cursor == front){
                deleteFront();
            }else if( cursor == back ){
                deleteBack();
            }else{
                cursor.previous.next = cursor.next;
                cursor.next.previous = cursor.previous;
                cursor = null;
                length--;
            }

        }

        // Other Functions ---------------------------------------------------------

        // toString()
        // Overides Object's toString() method.
        public String toString(){
            StringBuffer sb = new StringBuffer();
            Node N = front;
            while(N!=null){
                sb.append(N.toString());
                sb.append(" ");
                N = N.next;
            }
            return new String(sb);
        }

        // copy()
        // Returns a new List identical to this List.
        dm copy(){
            dm Q = new dm();
            Node N = this.front;

            while( N!=null ){
                Q.append(N.data);
                N = N.next;
            }
            return Q;
        }

    }
}
