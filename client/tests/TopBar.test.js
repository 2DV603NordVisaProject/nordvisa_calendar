import React from 'react';
import { shallow } from 'enzyme';
import TopBar from '../src/components/TopBar';
import ReactDom from 'react-dom';

// TODO; Integration test with child components
// TODO; Refactor, see comments below.

describe('TopBar', () => {
  let wrapper;
  const context = { language: {
    TopBar: '',
    swedish: '',
    english: '',
    danish: '',
    icelandic: '',
    norwegian: '',
  } };
  const onLanguageChange = jest.fn();

  beforeEach(() => {
    wrapper = shallow(<TopBar onLanguageChange={onLanguageChange} />, { context });
  });

  it('should render TopBar', () => {
    expect(wrapper.find('.topbar').length).toBe(1);
  });

  it('should render brand', () => {
    expect(wrapper.find('.brand').length).toBe(1);
  });

  it('should render language selection', () => {
    expect(wrapper.find('select').length).toBe(1);
  });

  it('should render 5 language options', () => {
    expect(wrapper.find('select option').length).toBe(5);
  });

  describe('user change language', () => {
    const value = 'sv';

    beforeEach(() => {
      const select = wrapper.find('select').first();
      select.simulate('change', {
        target: { value },
      });
    });

    // TODO; Refactoring
    it("should call prop 'onLanguageChange' with language", () => {
      const invocationArgs = onLanguageChange.mock.calls[0];
      expect(invocationArgs[0].target.value).toEqual(value);
    });
  });
});
