import React from 'react';
import '../../App.css';
import {Link} from "react-router-dom";


const MyNavbar = ({
                      searchBar, loggedInAsButton, logoutButton, sOUsername, filterTitle, onChangeFilter,
                      onSearchClick, filterText, onSearchChange, onLogout
                  }) => (
    <nav className="navbar navbar-dark fixed-top myNavbar">
        <div className="navbar-header">
            <Link className="navbar-brand navbar-light" to="/">
                <img src="https://i.ibb.co/khjjsKD/slackoverflow.png" width="129" height="30" alt=""/>
            </Link>
        </div>

        <ul className="navbar-nav nav mr-auto">
            <li className="nav-item">
                <Link className="nav-link" style={{"color": "#f7f9fb"}} to="/questions">
                    <strong>Home</strong></Link>
            </li>
        </ul>

        {
            searchBar === true ?
                <form className="nav form-inline mr-auto">
                    <input value={filterText}
                           onChange={e => onSearchChange("filterText", e.target.value)}
                           className="form-control mr-sm-2 search-input mySearch" type="search"
                           placeholder="Search..."/>
                    {
                        filterTitle === true ?
                            <button onClick={onChangeFilter} className="btn myButton btn-search my-2 my-sm-0 ">
                                Title
                            </button>
                            :
                            <button onClick={onChangeFilter} className="btn myButton btn-search my-2 my-sm-0 ">
                                Tag
                            </button>
                    }

                    <button onClick={onSearchClick} className="btn myButton btn-search my-2 my-sm-0 " type="submit">
                        Search
                    </button>
                </form>
                :
                <div/>
        }

        {
            (loggedInAsButton === true) ?
                <div id="loggedIn">Logged in as <span className="fakeLink" style={{"color": "#31708e"}}>
                <strong> {sOUsername} </strong></span></div> : <div/>
        }

        {
            (logoutButton === true) ? <button onClick={onLogout} className="btn nav-item myButton"> Logout </button> :
                <div/>
        }

    </nav>
);


export default MyNavbar;