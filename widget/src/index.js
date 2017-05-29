import React from 'react';
import ReactDOM from 'react-dom';
import Widget from './Widget';
import registerServiceWorker from './registerServiceWorker';

ReactDOM.render(<Widget />, document.getElementById('visa-widget'));
registerServiceWorker();
