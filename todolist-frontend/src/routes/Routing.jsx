import {BrowserRouter, Route, Routes} from "react-router-dom";
import React from "react";
import GroupsComponent from "../components/GroupsComponent";
import GroupForm from "../components/GroupForm";
import TaskForm from "../components/TaskForm";
import SignUp from "../components/SignUp";
import Identification from "../components/Identification";
import Login from "../components/Login";

const Routing = () => {

    return <BrowserRouter>
        <Routes>
            <Route element={<Identification/>} path='/'/>

            <Route element={<SignUp/>} path='/signup'/>
            <Route element={<Login/>} path='/login'/>

            <Route element={<GroupsComponent/>} path='/home'/>

            <Route element={<GroupForm/>} path='/add-group'/>
            <Route element={<GroupForm/>} path='/update-group'/>

            <Route element={<TaskForm/>} path='/add-task'/>
            <Route element={<TaskForm/>} path='/update-task'/>
        </Routes>
    </BrowserRouter>
}

export default Routing;