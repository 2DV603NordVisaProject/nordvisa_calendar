import React, { Component } from "react";
import "./MembersView.css";

class MembersView extends Component {

  //let membersMock = ["demo@gmail.com", "demo2@gmail.com", "demo3@gmail.com", "demo4@gmail.com", "demo5@gmail.com", "demo6@gmail.com", "demo7@gmail.com", "demo8@gmail.com", "demo9@gmail.com", "demo10@gmail.com"]

  render() {
    return (
      <div className="members">
        <h2>Members</h2>
        <form>
          <table>
            <tr>
              <th className="email-cell">Email</th>
              <th>Access Level</th>
            </tr>
            <tr>
              <td className="email-cell" >demo@hotmail.com</td>
              <td>
                <select>
                  <option selected>User</option>
                  <option>Admin</option>
                  <option>Superadmin</option>
                </select>
              </td>
            </tr>
            <tr>
              <td className="email-cell" >demo@hotmail.com</td>
              <td>
                <select>
                  <option selected>User</option>
                  <option>Admin</option>
                  <option>Superadmin</option>
                </select>
              </td>
            </tr>
            <tr>
              <td className="email-cell" >demo@hotmail.com</td>
              <td>
                <select>
                  <option selected>User</option>
                  <option>Admin</option>
                  <option>Superadmin</option>
                </select>
              </td>
            </tr>
          </table>
          <button>SAVE</button>
        </form>
      </div>
    );
  }
}

export default MembersView;
